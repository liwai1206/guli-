package com.atguigu.orderservice.client;


import com.atguigu.commonutils.vo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-edu")
@Component
public interface EduClient {

    @GetMapping("/eduservice/coursefront/getCourseInfoById/{courseId}")
    public CourseWebVoOrder getCourseInfoById(@PathVariable("courseId") String courseId );

}
