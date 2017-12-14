package com.huawei.l00379880.myblogbackend.controller.admin;

import com.huawei.l00379880.myblogbackend.entity.Type;
import com.huawei.l00379880.myblogbackend.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


/***********************************************************
 * @Description : 博客分类操作层
 * @author      : 梁山广
 * @date        : 2017/12/13 20:47
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        // pageable对象会根据url中的传参自动组装起来
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/typeAdd")
    public String typeAdd() {
        return "admin/type-add";
    }

    @PostMapping("/typeAdd")
    public String typeAddPost(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        // 校验分类是不是已经有了
        Type tt = typeService.getTypeByName(type.getName());
        if (tt != null) {
            result.rejectValue("name", "nameError", "分类已经存在，不能重复添加！");
        }
        // 出现异常直接返回添加页面
        if (result.hasErrors()) {
            return "admin/type-add";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            // 未保存成功,传给前端一个提示
            attributes.addFlashAttribute("message", "添加博客分类失败！");
        } else {
            // 保存成功,传给前端一个提示
            attributes.addFlashAttribute("message", "添加博客分类成功！");
        }
        return "redirect:/admin/types";
    }
}