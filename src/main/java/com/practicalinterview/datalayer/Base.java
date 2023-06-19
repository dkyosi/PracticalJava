package com.practicalinterview.datalayer;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Base {
    @Column(name = "active")
    private boolean active;
    @Column(name = "deleted")
    private boolean deleted;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt")
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt")
    private Date updatedAt;
    @PrePersist
    public void base(){
        this.active=true;
        this.createdAt=new Date();
        this.updatedAt=new Date();
        this.deleted=false;
    }
}
