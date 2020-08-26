package io.skyease.air.eas.processor.pmml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.skyease.air.eas.processor.pmml.common.Response;
import io.skyease.air.eas.processor.pmml.common.InputRecord;
import io.skyease.air.eas.processor.pmml.util.PredictUtil;

/**
 * PredictController
 *
 * @author chenww
 * @date 2020-07-31
 * @time 10:18
 */
@RestController
@RequestMapping("/api")
public class PredictController {

    @Autowired
    private PredictUtil predictUtil;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }


    @PostMapping("/predict")
    public Response predict(@RequestBody InputRecord inputRecord) {
        return Response.success(predictUtil.predict(inputRecord));
    }

}
