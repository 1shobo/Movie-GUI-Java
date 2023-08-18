/**
 * @author lukeraeside
 * 
 * This class will be run at the start of the assignment so that the student can register the
 * machine and java versio being used for the duration of the assignment
 * 
 * Run this first!!
 * 
 */
package logging;

public class RegisterComputer {

	public static void main(String[] args) {
		RegisterComputer reg = new RegisterComputer();
		reg.register();
	}

	/**
	 *  This is the first method to run in each module to register when the work on the module began
	 */
	private void register() {
		AssignmentLogger.register(this);
	}

}
