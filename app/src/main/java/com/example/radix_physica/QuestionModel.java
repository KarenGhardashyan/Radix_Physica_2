package com.example.radix_physica;

public class QuestionModel {
    private String question;
    private String answer;
    private String topic;

    public QuestionModel() {
    }

    public QuestionModel(String question, String answer, String topic) {
        this.question = question;
        this.answer = answer;
        this.topic = topic;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public QuestionModel(String answer) {
        this.answer = answer;
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

    @Override
    public String toString() {
        return "Вопрос: " + question + "\nОтвет: " + answer + "\nТема: " + topic;
    }
}
