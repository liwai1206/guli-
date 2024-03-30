package com.atguigu.serviceedu.controller.front;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.CourseWebVoOrder;
import com.atguigu.serviceedu.client.OrderClient;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.chapter.ChapterVO;
import com.atguigu.serviceedu.entity.vo.CourseInfoVO;
import com.atguigu.serviceedu.entity.vo.CourseQueryVo;
import com.atguigu.serviceedu.entity.vo.CourseWebVo;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin
@RequestMapping("/eduservice/coursefront")
public class EduFrontCourseController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("pageList/{page}/{limit}")
    public R pageList(@PathVariable long page, @PathVariable long limit,
                      @RequestBody CourseQueryVo courseQueryVo){

        Page<EduCourse> coursePage = new Page<>(page, limit);

        Map<String, Object> map = courseService.pageListFront(coursePage, courseQueryVo );

        return R.ok().data( map );
    }

    @GetMapping("courseFrontDetail/{courseId}")
    public R courseFrontDetail(@PathVariable String courseId, HttpServletRequest request){
        // 查询课程信息，封装为courseWebVo对象
        CourseWebVo courseWebVo = courseService.findCourseDetailInfo( courseId );

        // 查询课程的章节与小节
        List<ChapterVO> chapterVideoList = chapterService.findAllChapterVideo(courseId );

        // 查询课程是否已经购买
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty( memberId )){
            return R.error().code(20001).message("请登陆后再访问!");
        }
        boolean isbuyCourse = orderClient.isBuyCourse(courseId, memberId);

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList", chapterVideoList).data("isbuyCourse", isbuyCourse);
    }

    @GetMapping("getCourseInfoById/{courseId}")
    public CourseWebVoOrder getCourseInfoById(@PathVariable String courseId ){
        // 查询课程信息，封装为courseWebVo对象
        CourseWebVo courseWebVo = courseService.findCourseDetailInfo( courseId );
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties( courseWebVo, courseWebVoOrder );
        return courseWebVoOrder;
    }
}
