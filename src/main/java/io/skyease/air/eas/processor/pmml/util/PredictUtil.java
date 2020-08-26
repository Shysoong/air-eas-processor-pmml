package io.skyease.air.eas.processor.pmml.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.bind.JAXBException;

import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.EvaluatorUtil;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.LoadingModelEvaluatorBuilder;
import org.jpmml.evaluator.OutputField;
import org.jpmml.evaluator.TargetField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import io.skyease.air.eas.processor.pmml.common.InputRecord;

/**
 * PredictUtil
 *
 * @author chenww
 * @date 2020-07-31
 * @time 10:22
 */
@Component
public class PredictUtil {
    private static final String MODEL_FILE_PATH = "/eas/modelFile/";

    @Value("${processor.modelFileName}")
    private String modelFileName;

    public Map<String, ?> predict(InputRecord inputRecord) {
        Map<String, ?> resultRecord;
        // Building a model evaluator from a PMML file
        Evaluator evaluator = null;
        try {
            String filePath = MODEL_FILE_PATH + modelFileName ;
            File file = new File(filePath);
            evaluator = new LoadingModelEvaluatorBuilder().load(file).build();
        } catch (IOException | SAXException | JAXBException e) {
            e.printStackTrace();
        }

        // Perforing the self-check
        Objects.requireNonNull(evaluator).verify();

        // Printing input (x1, x2, .., xn) fields
        List<? extends InputField> inputFields = evaluator.getInputFields();
        System.out.println("Input fields: " + inputFields);

        // Printing primary result (y) field(s)
        List<? extends TargetField> targetFields = evaluator.getTargetFields();
        System.out.println("Target field(s): " + targetFields);

        // Printing secondary result (eg. probability(y), decision(y)) fields
        List<? extends OutputField> outputFields = evaluator.getOutputFields();
        System.out.println("Output fields: " + outputFields);

        Map<FieldName, FieldValue> arguments = new LinkedHashMap<>();

        // Mapping the record field-by-field from data source schema to PMML schema
        for (InputField inputField : inputFields) {
            FieldName inputName = inputField.getName();

            Object rawValue = inputRecord.get(inputName.getValue());

            // Transforming an arbitrary user-supplied value to a known-good PMML value
            FieldValue inputValue = inputField.prepare(rawValue);

            arguments.put(inputName, inputValue);
        }

        // Evaluating the model with known-good arguments
        Map<FieldName, ?> results = evaluator.evaluate(arguments);

        // Decoupling results from the JPMML-Evaluator runtime environment
        resultRecord = EvaluatorUtil.decodeAll(results);

        System.out.println("result fields: " + resultRecord);

        // Making the model evaluator eligible for garbage collection
        evaluator = null;
        return resultRecord;
    }
}

