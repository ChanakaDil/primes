import akka.actor.UntypedActor;
import dto.NumberRangeMessage;
import dto.Result;

/**
 * Created by chanaka on 6/14/17.
 */
public class PrimeWorker extends UntypedActor {


    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof NumberRangeMessage) {
            NumberRangeMessage msg = (NumberRangeMessage) message;

            long startNumber = msg.getStartNumber();
            long endNumber = msg.getEndNumber();

            System.out.println("Number Range : " + startNumber + " to " + endNumber);

            Result result = new Result();

            for (long i = startNumber; i <= endNumber; i++) {
                if (isPrime(i)) {
                    result.getResults().add(i);
                }
            }

            getSender().tell(result, getSelf());
        } else {
            unhandled(message);
        }
    }

    private boolean isPrime(long number) {
        int count = 0;

        for (int j = 1; j <= number; j++) {
            if (number % j == 0) {
                count++;
            }
        }

        if (count == 2) {
            return true;
        }
        return false;

    }
}
