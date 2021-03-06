package it.unisannio.catman.domain.workflow;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import it.unisannio.catman.common.server.AbstractEntity;
import it.unisannio.catman.domain.documents.Document;
import it.unisannio.catman.domain.documents.Dossier;
import it.unisannio.catman.domain.planning.Plan;

@Entity
public class Event extends AbstractEntity<Long> implements Dossier<EventStatus, Event>{
	
	public static Event findEvent(Long id) {
		return find(Event.class, id);
	}
	
	public static List<Event> findAll() {
		return findAll(Event.class);	
	}
	
	public static List<Event> listAll(int start, int length) {
		return list(Event.class, start, length);
	}
	
	public static void delete(List<Long> keys) {
		deleteByKeys(Event.class, keys);
	}
	
	public static int count() {
		return count(Event.class);
	}
	
	//FIXME Rielaborare
	public static List<Event> listBy(String searchQuery, Date date, int start, int length) {
		String query = "SELECT e FROM Event e";
		if((searchQuery != null && !searchQuery.trim().equals("")) || date!=null){
			query += " WHERE ";
			if(searchQuery != null && !searchQuery.trim().equals("")){
				query += "lower(e.title) LIKE '%"+searchQuery.toLowerCase()+"%'";
				if(date != null)
					query += " AND e.startDate = ?1";
			}else if(date != null)
				query += "e.startDate = ?1";
		}
		if(date!=null)
			return listByQuery(Event.class, start, length, query, date);
		return listByQuery(Event.class, start, length, query);
	}
	
	//FIXME Rielaborare
		public static int countBy(String searchQuery, Date date) {
			String query = "SELECT COUNT(e) FROM Event e";
			if((searchQuery != null && !searchQuery.trim().equals("")) || date!=null){
				query += " WHERE ";
				if(searchQuery != null && !searchQuery.trim().equals("")){
					query += "lower(e.title) LIKE '%"+searchQuery.toLowerCase()+"%'";
					if(date != null)
						query += " AND e.startDate = ?1";
				}else if(date != null)
					query += "e.startDate = ?1";
			}
			if(date!=null)
				return countByQuery(query, date);
			return countByQuery(query);
		}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Version private int version;
	
	@ManyToOne
	private Customer customer;
	
	private String title;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@OneToMany(mappedBy="dossier", cascade = CascadeType.ALL)
	private List<EventDocument> documents;
	
	private EventStatus status;
	
	public Event() { }

	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public Long getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

	@Override
	public EventStatus getStatus() {
		return status;
	}
	
	public void setStatus(EventStatus es) {
		this.status = es;
	}

	@Override
	public void addDocument(Document<EventStatus,Event> d) {
		documents.add((EventDocument) d);
		d.setDossier(this);
		
	}

	@Override
	public void removeDocument(Document<EventStatus,Event> d) {
		documents.remove((EventDocument) d);
		d.setDossier(null);
	}

	@Override
	public List<? extends Document<EventStatus, Event>> getDocuments() {
		return documents;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Plan addPlan() {
		Plan plan = new Plan();
		
		plan.setDossier(this);
		addDocument(plan);
		
		return plan;
	}
	
}
