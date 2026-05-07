package com.ai.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ai/milvus")
public class MilvusTestController {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private ChatModel chatModel;

//    @Autowired
//    private ChatClient chatClient;

    //新增文档 自动向量化 存入milvus
    @GetMapping("/add")
    public String add(@RequestParam("content") String content) {
        Document document = new Document(content);
        vectorStore.add(List.of(document));
        return "success";
    }

    //搜索
    @GetMapping(value = "/ask", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> ask(@RequestParam("question") String question) {
        //1.搜索模型相关向量
        List<Document> docs = vectorStore.similaritySearch(question);
        //2.拼接上下文
        StringBuilder context = new StringBuilder();
        for (Document doc : docs) {
            context.append(doc.getText()).append("\n");
        }
        // 1. 定义模板（带变量）
        String systemPrompt = """
                你是一个专业的知识库助手，严格按照参考资料回答。
                不知道就说：未找到相关信息。
                参考资料：{context}
                用户问题：{question}
                """;

        // 2. 创建模板对象
        PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);
        // 3. 填充变量
        Map<String, Object> map = new HashMap<>();
        map.put("context", context);
        map.put("question", question);
        Prompt prompt = promptTemplate.create(map);
        return chatModel.stream(prompt).map(ChatResponse::getResult).mapNotNull(result -> result.getOutput().getText());
    }

}
