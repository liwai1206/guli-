package com.atguigu.serviceedu.client;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    @DeleteMapping("/eduvod/filevod/deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id);

    @DeleteMapping("/eduvod/filevod/deleteBatchVideo")
    public R deleteBatchVideo(@RequestBody List<String> ids);
}
