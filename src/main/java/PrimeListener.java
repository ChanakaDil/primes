import akka.actor.UntypedActor;
import dto.Result;

/**
 * Created by chanaka on 6/14/17.
 */
public class PrimeListener extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof Result) {
            System.out.print("Results : ");
            for (long val : ((Result) message).getResults()) {
                System.out.print(val + ", ");
            }
            System.out.println();
            getContext().system().shutdown();
        } else {
            unhandled(message);
        }

    }
}
