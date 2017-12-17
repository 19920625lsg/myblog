package com.huawei.l00379880.myblogbackend.controller.admin;

import com.huawei.l00379880.myblogbackend.entity.Tag;
import com.huawei.l00379880.myblogbackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


/***********************************************************
 * @Description : 博客标签操作层
 * @author      : 梁山广
 * @date        : 2017/12/13 20:47
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        // pageable对象会根据url中的传参自动组装起来
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tagAdd")
    public String tagAdd(Model model) {
        // 不谢这一句前端会出现Neither BindingResult nor plain target object for bean name 'tag'
        model.addAttribute("tag", new Tag());
        return "admin/tag-add";
    }

    @PostMapping("/tagAdd")
    public String tagAddPost(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        // 校验分类是不是已经有了
        Tag tt = tagService.getTagByName(tag.getName());
        if (tt != null) {
            result.rejectValue("name", "nameError", "标签已经存在，不能重复添加！");
        }
        // 出现异常直接返回添加页面
        if (result.hasErrors()) {
            return "admin/tag-add";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            // 未保存成功,传给前端一个提示
            attributes.addFlashAttribute("message", "添加博客标签失败！");
        } else {
            // 保存成功,传给前端一个提示
            attributes.addFlashAttribute("message", "添加博客标签成功！");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tagEdit/{id}")
    public String tagAddPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        // 校验分类是不是已经有了
        Tag tt = tagService.getTagByName(tag.getName());
        if (tt != null) {
            result.rejectValue("name", "nameError", "博客标签已存在！！");
        }
        // 出现异常直接返回添加页面
        if (result.hasErrors()) {
            return "admin/tag-add";
        }
        // 根据id更新分类
        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            // 未保存成功,传给前端一个提示
            attributes.addFlashAttribute("message", "修改博客标签失败！");
        } else {
            // 保存成功,传给前端一个提示
            attributes.addFlashAttribute("message", "修改博客标签成功！");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tag/{id}/edit")
    public String editTag(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tag-add";
    }

    @GetMapping("/tag/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除博客标签成功！");
        return "redirect:/admin/tags";
    }

}
