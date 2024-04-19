/*
 * Copyright 2021-2024 OpenAIRE AMKE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gr.athenarc.ticketingsystem.domain;

import java.util.Date;

public class Comment {

    private User from;
    private String text;
    private Date date;

    public Comment() {
        // no arg constructor
    }

    public Comment(User from, String text, Date date) {
        this.from = from;
        this.text = text;
        this.date = date;
    }

    public Comment(Comment comment) {
        this(comment.getFrom(), comment.getText(), Date.from(comment.getDate().toInstant()));
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
