package com.example.radix_physica.AddQuizAndQuestion;

public class ModeratedQuestion {
    private String questionId;
    private String question;
    private String answer;
    private String topic;
    private String moderatorId;
    private long moderationTime;

    public ModeratedQuestion() {
    }


    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
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

    public String getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(String moderatorId) {
        this.moderatorId = moderatorId;
    }

    public long getModerationTime() {
        return moderationTime;
    }

    public void setModerationTime(long moderationTime) {
        this.moderationTime = moderationTime;
    }

    public ModeratedQuestion(String questionId, String question, String answer, String topic, String moderatorId, long moderationTime) {
        this.questionId = questionId;
        this.question = question;
        this.answer = answer;
        this.topic = topic;
        this.moderatorId = moderatorId;
        this.moderationTime = moderationTime;
    }
}
