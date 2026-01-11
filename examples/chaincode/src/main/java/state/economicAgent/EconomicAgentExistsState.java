package state.economicAgent;

import entity.EconomicAgent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class EconomicAgentExistsState extends EconomicAgentState {
  //========== Constructor ===========
  public EconomicAgentExistsState() {
    super("exists", StateType.ONGOING);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---
  @Override
  public void handle_EVcrOwnership(EconomicAgent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicAgent (object)
    EconomicAgentExistsState newState = new EconomicAgentExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendIncrement(EconomicAgent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicAgent (object)
    EconomicAgentExistsState newState = new EconomicAgentExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendParticipation(EconomicAgent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicAgent (object)
    EconomicAgentExistsState newState = new EconomicAgentExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendDecrement(EconomicAgent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicAgent (object)
    EconomicAgentExistsState newState = new EconomicAgentExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrIncrement(EconomicAgent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicAgent (object)
    EconomicAgentExistsState newState = new EconomicAgentExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrDecrement(EconomicAgent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicAgent (object)
    EconomicAgentExistsState newState = new EconomicAgentExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendDependentView(EconomicAgent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicAgent (object)
    EconomicAgentExistsState newState = new EconomicAgentExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrDependentView(EconomicAgent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicAgent (object)
    EconomicAgentExistsState newState = new EconomicAgentExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrParticipation(EconomicAgent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicAgent (object)
    EconomicAgentExistsState newState = new EconomicAgentExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_endLastDependenctView(EconomicAgent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicAgent (object)
    EconomicAgentExistsState newState = new EconomicAgentExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendOwnership(EconomicAgent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicAgent (object)
    EconomicAgentExistsState newState = new EconomicAgentExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }

  //--- Ending Events ---
  @Override
  public void handle_EVendEconomicAgent(EconomicAgent object, Context ctx) throws FailedEventHandlingException {

    //Check if there are living  dependents before ending the object
    if(StubHelper.hasLivingDependents(ctx, object))
      throw new FailedEventHandlingException("EconomicAgent (" + object.getId() + ") has living dependents and therefore cannot be ended");

    //Changes the state of the current EconomicAgent (object)
    EconomicAgentEndedState newState = new EconomicAgentEndedState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
}
