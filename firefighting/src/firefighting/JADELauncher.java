package firefighting;

import jade.Boot;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import firefighting.AircraftAgent;

/**
 * Class responsible for running and controlling JADE execution
 */
public class JADELauncher {
    static World world = new World();
	
	public static void main(String[] args) {		
		Runtime rt = Runtime.instance();
		
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.GUI, "true");
		ContainerController mainContainer = rt.createMainContainer(profile);
		
	
		try {
			AgentController testagent = mainContainer.createNewAgent("name2", "firefighting.AircraftAgent" , null);
			testagent.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}