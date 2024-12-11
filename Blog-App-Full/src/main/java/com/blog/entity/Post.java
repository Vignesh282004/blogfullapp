package com.blog.entity;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @Column(nullable = false)
	    private String title;
	    private String url;

	    @Lob
	    @Column(nullable = false, columnDefinition = "LONGTEXT")
	    private String content;
	    
	    private String shortDescription;
	    
	    //when we create a Post object, Hibernate will automatically fill the createdOn field with the current timestamp.
	    @CreationTimestamp
	    private LocalDateTime createdOn;
	    
	    //when we update a Post object, Hibernate will automatically fill the updatedOn field with the updated timestamp.
	    @UpdateTimestamp
	    private LocalDateTime updatedOn;
	    
	    // when we delete the post, the comments should be deleted too = CascadeType.REMOVE
	    @ManyToOne
	    @JoinColumn(name = "createdBy",nullable = false)
	    private User createdBy;
	    
	    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
	    private Set<Comment> comments = new HashSet<>();
}
