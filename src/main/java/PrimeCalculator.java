import akka.actor.*;
import dto.NumberRangeMessage;

/**
 * Created by chanaka on 6/14/17.
 */
public class PrimeCalculator {

    public void calculate(long start, long end) {
        ActorSystem system = ActorSystem.create("PrimeCalculator");
        final ActorRef primeListener = system.actorOf(new Props(PrimeListener.class));

        ActorRef primeMaster = system.actorOf(new Props(new UntypedActorFactory() {
            @Override
            public Actor create() throws Exception {
                return new PrimeMaster(10, primeListener);
            }
        }), "primeMaster");

        primeMaster.tell(new NumberRangeMessage(start, end));
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please enter start and end....!");
            System.exit(0);
        }

        long start = Long.parseLong(args[0]);
        long end = Long.parseLong(args[1]);

        PrimeCalculator cal = new PrimeCalculator();
        cal.calculate(start, end);
    }
}
