package com.example.radix_physica.AddQuizAndQuestion;

public class QuestionModel {
    private String question;
    private String answer;
    private String topic;
    private boolean moderated;

    public QuestionModel() {}

    public QuestionModel(String question, String answer, String topic) {
        this.question = question;
        this.answer = answer;
        this.topic = topic;
        this.moderated = false;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean isModerated() {
        return moderated;
    }

    public void setModerated(boolean moderated) {
        this.moderated = moderated;
    }
}
