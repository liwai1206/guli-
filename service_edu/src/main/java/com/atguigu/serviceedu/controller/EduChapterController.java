package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.chapter.ChapterVO;
import com.atguigu.serviceedu.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 22wli
 * @since 2023-06-22
 */
@RestController
@RequestMapping("/eduservice/educhapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("/getChapterVideoList/{courseId}")
    public R getChapterVideoList(@PathVariable String courseId){

        List<ChapterVO> chapterVOList = eduChapterService.findAllChapterVideo(courseId);

        return R.ok().data("chapterVOList", chapterVOList);
    }


    // 新增章节
    @PostMapping("saveChapter")
    public R save( @RequestBody EduChapter chapter){
        boolean save = eduChapterService.save(chapter);

        if ( save ){
            return R.ok();
        }else {
            return R.error();
        }
    }

    // 查询章节信息
    @GetMapping("getChapter/{chapterId}")
    public R getChapterById( @PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("eduChapter", eduChapter );
    }

    // 更新章节信息
    @PostMapping("update")
    public R updateChapter( @RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    // 删除章节信息
    @DeleteMapping("delete/{chapterId}")
    public R deleteChapter(  @PathVariable String chapterId){
        boolean result = eduChapterService.removeChapterById( chapterId );
        if ( result ){
            return R.ok();
        }else {
            return R.error().message("删除失败");
        }

    }

}

