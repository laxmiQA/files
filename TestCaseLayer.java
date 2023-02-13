package genericByYadavRahul;

public class TestCaseLayer {
	public void m1(int a){
		a=28;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Utility obj = new Utility("learn");
//		obj.report();
		obj.createTest("tcoo1");
System.out.println("updated");
		obj.browserLaunch("Chrome");
		obj._hitURL("http://localhost:8888");
		obj.getElement("name", "user_name");
		obj.send("name", "user_name", "usernamebox", "admin");
		obj.sizeOfElement("name", "user_name", "UserNameBox", 22, 140);
		obj.getElement("password", "user_password");
		obj.send("name", "user_password", "UserPasswordbox", "admin");
		obj.sizeOfElement("name", "user_password", "Userpasswordbox", 23, 145);
		obj.getElement("name", "Login");
		obj.sizeOfElement("name","Login","loginbox" , 05, 5);
		obj.click("name","Login", "Loginbox");
		obj.flushed();
	}
}
