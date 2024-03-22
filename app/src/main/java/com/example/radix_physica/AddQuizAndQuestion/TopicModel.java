package com.example.radix_physica.AddQuizAndQuestion;

public class TopicModel {
    private String topicHead;
    private String topicContent;
    private String webLinks;

    public TopicModel() {
    }

    public TopicModel(String topicHead, String topicContent, String webLinks) {
        this.topicHead = topicHead;
        this.topicContent = topicContent;
        this.webLinks = webLinks;
    }

    public String getTopicHead() {
        return topicHead;
    }

    public void setTopicHead(String topicHead) {
        this.topicHead = topicHead;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public String getWebLinks() {
        return webLinks;
    }

    public void setWebLinks(String webLinks) {
        this.webLinks = webLinks;
    }
}
