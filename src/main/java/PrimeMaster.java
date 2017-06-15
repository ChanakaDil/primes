import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;
import dto.NumberRangeMessage;
import dto.Result;

/**
 * Created by chanaka on 6/14/17.
 */
public class PrimeMaster extends UntypedActor {

    private final ActorRef listner;

    private final ActorRef router;

    private final int numberOfWorkers;

    private int numberOfResults = 0;

    private Result finalResults = new Result();

    public PrimeMaster(int numberOfWorkers, ActorRef listner) {
        this.listner = listner;
        this.numberOfWorkers = numberOfWorkers;

        router = this.getContext().actorOf(new Props(PrimeWorker.class).withRouter(new RoundRobinRouter(numberOfWorkers)), "router");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof NumberRangeMessage) {
            NumberRangeMessage msg = (NumberRangeMessage) message;
            long count = msg.getEndNumber() - msg.getStartNumber();
            long segLength = count / 10;

            for (int i = 0; i < numberOfWorkers; i++) {
                long startNumber = msg.getStartNumber() + i*segLength;
                long endNumber = startNumber + segLength - 1;
                if (i == numberOfWorkers - 1) {
                    endNumber = msg.getEndNumber();
                }
                router.tell(new NumberRangeMessage(startNumber, endNumber), getSelf());
            }

        } else if (message instanceof Result) {

            Result result = (Result) message;
            finalResults.getResults().addAll(result.getResults());

            if (++numberOfResults >= 10) {
                listner.tell(finalResults, getSelf());

                getContext().stop(getSelf());
            }

        } else {
            unhandled(message);
        }
    }
}
