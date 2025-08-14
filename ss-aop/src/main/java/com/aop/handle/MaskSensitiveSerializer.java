package com.aop.handle;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class MaskSensitiveSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private int prefix;
    private int suffix;
    private char maskChar;


    /**
     * 执行实际的转换逻辑，将原始值处理后写入 JSON。
     * @param s
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(s == null || s.isEmpty()){
            jsonGenerator.writeNull();
            return;
        }
        //执行脱敏逻辑
        int length = s.length();
        //确保前缀和后缀不会重叠
        int maskLength = Math.max(0,length - prefix - suffix);

        //构建脱敏后的字符串
        String maskedString = s.substring(0, prefix) +
                String.valueOf(maskChar).repeat(maskLength) +
                s.substring(length - suffix);
        jsonGenerator.writeString(maskedString);
    }

    /**
     * 从字段上下文获取配置信息
     * @param serializerProvider
     * @param beanProperty
     * @return
     * @throws JsonMappingException
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if(beanProperty != null){
            //检查字段是否标注了我们的自定义注解
            MaskSensitive annotation = beanProperty.getAnnotation(MaskSensitive.class);
            if(annotation != null){
                return new MaskSensitiveSerializer(
                        annotation.prefix(),
                        annotation.suffix(),
                        annotation.maskChar()
                );
            }
        }
        return this;
    }
}
