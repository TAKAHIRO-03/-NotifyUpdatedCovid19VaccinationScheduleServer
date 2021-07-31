package jp.co.tk.nucvs.domain.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sun.istack.NotNull;

@MappedSuperclass
public abstract class RevisionInfo {

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdTime;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date updatedTime;

	@PreUpdate
	@PrePersist
	private void updateTimestamps() {
		if (createdTime == null) {
			createdTime = new Date();
		}
		updatedTime = new Date();
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdateTime() {
		return updatedTime;
	}

	void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
}