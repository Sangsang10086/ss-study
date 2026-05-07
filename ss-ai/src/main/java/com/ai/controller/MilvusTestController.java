package com.ai.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ai/milvus")
public class MilvusTestController {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private ChatModel chatModel;

    //新增文档 自动向量化 存入milvus
    @GetMapping("/add")
    public String add(@RequestParam("content") String content) {
        Document document = new Document(content);
        vectorStore.add(List.of(document));
        return "success";
    }

    //搜索
    @GetMapping("/ask")
    public String ask(@RequestParam("question") String question) {
        //1.搜索模型相关向量
        List<Document> docs = vectorStore.similaritySearch(question);

        //2.拼接上下文
        StringBuilder context = new StringBuilder();
        for (Document doc : docs) {
            context.append(doc.getText()).append("\n");
        }

        //3.交给模型处理
        String prompt = "根据以下资料回答问题：\n" +
                context + "\n" +
                "问题：" + question;
        return chatModel.call(prompt);
    }

}
