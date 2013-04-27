package com.flashcard.dto;

/**
 * User: ghaxx
 * Date: 27/04/2013
 * Time: 13:02
 */
public class WordDTO {
    Integer id;
    String in_english;
    String in_polish;
    String created_at;
    String updated_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIn_english() {
        return in_english;
    }

    public void setIn_english(String in_english) {
        this.in_english = in_english;
    }

    public String getIn_polish() {
        return in_polish;
    }

    public void setIn_polish(String in_polish) {
        this.in_polish = in_polish;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "WordDTO{" +
                "created_at='" + created_at + '\'' +
                ", id=" + id +
                ", in_english='" + in_english + '\'' +
                ", in_polish='" + in_polish + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
