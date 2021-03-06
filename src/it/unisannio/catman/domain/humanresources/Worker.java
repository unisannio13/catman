package it.unisannio.catman.domain.humanresources;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import it.unisannio.catman.domain.contacts.Contactable;

@Entity
public class Worker extends Contactable {
	public static Worker findWorker(Long id) {
		return find(Worker.class, id);
	}

	public static List<Worker> findAll() {
		return findAll(Worker.class);	
	}

	public static List<Worker> listAll(int start, int length){
		return list(Worker.class, start,length);
	}

	public static int count(){
		return count(Worker.class);
	}

	public static List<Worker> listByQualificationInWorkersSource(Qualification qualification, int start, int length){
		return listByQuery(Worker.class, start, length,"SELECT w FROM JobBoard jb " +
				"RIGHT OUTER JOIN jb.workers w " +
				"INNER JOIN w.pieceworks pw "+
				"WHERE w.candidate = ?1 " +
				"AND jb IS NULL " +
				"AND pw.qualification = ?2",false,qualification);
	}

	public static int countByQualificationInWorkersSource(Qualification qualification){
		return countByQuery("SELECT COUNT(w) FROM JobBoard jb " +
				"RIGHT OUTER JOIN jb.workers w " +
				"INNER JOIN w.pieceworks pw "+
				"WHERE w.candidate = ?1 " +
				"AND jb IS NULL " +
				"AND pw.qualification = ?2", false, qualification);
	}

	public static List<Worker> listByQualificationInCandidates(Qualification qualification, int start, int length){
		return listByQuery(Worker.class, start, length,"SELECT w FROM Worker w " +
				"INNER JOIN w.pieceworks pw "+
				"WHERE w.candidate = ?1 " +
				"AND pw.qualification = ?2", true, qualification);
	}

	public static int countByQualificationInCandidates(Qualification qualification){
		return countByQuery("SELECT COUNT(w) FROM Worker w " +
				"INNER JOIN w.pieceworks pw "+
				"WHERE w.candidate = ?1 " +
				"AND pw.qualification = ?2", true, qualification);
	}

	public static List<Worker> listByQualificationInJobBoard(Qualification qualification, JobBoard jobBoard, int start, int length){
		return listByQuery(Worker.class, start, length,"SELECT w FROM JobBoard jb " +
				"INNER JOIN jb.workers w " +
				"INNER JOIN w.pieceworks pw "+
				"WHERE jb = ?1 " +
				"AND pw.qualification = ?2", jobBoard, qualification);
	}

	public static int countByQualificationInJobBoard(Qualification qualification, JobBoard jobBoard){
		return countByQuery("SELECT COUNT(w) FROM JobBoard jb " +
				"INNER JOIN jb.workers w " +
				"INNER JOIN w.pieceworks pw "+
				"WHERE jb = ?1 " +
				"AND pw.qualification = ?2", jobBoard, qualification);
	}

	public static List<Worker> listInWorkersSource(int start, int length){
		return listByQuery(Worker.class, start, length, "SELECT w FROM  JobBoard jb " +
				"RIGHT OUTER JOIN jb.workers w " +
				"WHERE w.candidate = ?1 " +
				"AND jb IS NULL",false);
	}

	public static List<Worker> listInCandidates(int start, int length){
		return listByQuery(Worker.class, start, length, "SELECT w FROM Worker w " +
				"WHERE w.candidate = ?1",true);
	}

	public static List<Worker> listByJobBoard(JobBoard jobBoard, int start, int length){
		return listByQuery(Worker.class, start, length, "SELECT w FROM JobBoard jb " +
				"INNER JOIN jb.workers w " +
				"WHERE jb = ?1", jobBoard);
	}

	public static int countInWorkersSource(){
		return countByQuery("SELECT COUNT(w) FROM  JobBoard jb " +
				"RIGHT OUTER JOIN jb.workers w " +
				"WHERE w.candidate = ?1 " +
				"AND jb IS NULL",false);
	}

	public static int countInCandidates(){
		return countByQuery("SELECT COUNT(w) FROM Worker w WHERE w.candidate = ?1",true);
	}

	public  static int countByJobBoard(JobBoard jobBoard){
		return countByQuery("SELECT COUNT(w) FROM JobBoard jb " +
				"INNER JOIN jb.workers w " +
				"WHERE jb = ?1", jobBoard);
	}
	
	
	public static List<Worker> findByQualification(Qualification qualification){
		return findByQuery("SELECT w FROM Worker w " +
				"INNER JOIN w.pieceworks pw "+
				"WHERE pw.qualification = ?1", qualification);
	}
	
	public static List<Worker> listByQualification(Qualification qualification, int start, int length){
		return listByQuery(Worker.class, start, length,"SELECT w FROM Worker w " +
				"INNER JOIN w.pieceworks pw "+
				"WHERE pw.qualification = ?1", qualification);
	}

	public static int countByQualification(Qualification qualification){
		return countByQuery("SELECT COUNT(w) FROM Worker w " +
				"INNER JOIN w.pieceworks pw "+
				"WHERE pw.qualification = ?1",  qualification);
	}

	@Id 
	@GeneratedValue
	private long id;

	@Version 
	private int version;

	private boolean candidate;

	private String resume;

	@OneToMany(mappedBy="worker", cascade = {CascadeType.ALL})
	private Set<Piecework> pieceworks = new HashSet<Piecework>();

	public Set<Qualification> getQualifications() {
		Set<Qualification> uniqueQualifications = new HashSet<Qualification>();
		for(Piecework p : pieceworks) {
			uniqueQualifications.add(p.getQualification());
		}
		
		return uniqueQualifications;
	}
	
	public Set<Piecework> getPieceworks() {
		return pieceworks;
	}

	public void addPiecework(Piecework q) {
		pieceworks.add(q);
	}

	public void removePiecework(Piecework q) {
		pieceworks.remove(q);
	}

	public boolean hasQualification(Qualification q) {
		return getQualifications().contains(q);
	}

	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public Long getId() {
		return id;
	}

	public boolean isCandidate() {
		return candidate;
	}

	public void setCandidate(boolean candidate) {
		this.candidate = candidate;
	}
	
	public Collection<Contract> getContracts() {
		return Contract.findByWorker(this);
	}

	public boolean isWorking(){
		for(Contract c : getContracts()) {
			if(c.isCurrent())
				return true;
		}

		return false;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}
}
