package firefighting;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;

/**
 * Class responsible for running and controlling JADE execution
 */
public class JADELauncher {
	static World world = new World();
	
	public static void main(String[] args) {
		world.startWorld();
		world.printCurrentStatus();
		
		/**Runtime rt = Runtime.instance();

		Profile profile = new ProfileImpl();

		
		    profile.setParameter(Profile.CONTAINER_NAME, "Pacman");
		    profile.setParameter(Profile.MAIN_HOST, "localhost");
		    profile.setParameter(Profile.GUI, "true");
	    
	    
		ContainerController mainContainer = rt.createMainContainer(profile);*/
	}

}
