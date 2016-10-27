package sample.jpastudy.member.model;



public class MemberTemp {
	
	private String id;
	
	private String name;
	
	private String teamId;


	public MemberTemp() {
	}

	
	

	public MemberTemp(String id, String name) {
		this.id = id;
		this.name = name;
	}



	
	public MemberTemp(String id, String name, String teamId) {
		this.id = id;
		this.name = name;
		this.teamId = teamId;
	}




	public String getTeamId() {
		return teamId;
	}
	
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
