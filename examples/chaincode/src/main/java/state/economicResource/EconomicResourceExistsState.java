package state.economicResource;

import entity.EconomicResource;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class EconomicResourceExistsState extends EconomicResourceState {
  //========== Constructor ===========
  public EconomicResourceExistsState() {
    super("exists", StateType.ONGOING);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---
  @Override
  public void handle_EVcrOwnership(EconomicResource object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicResource (object)
    EconomicResourceExistsState newState = new EconomicResourceExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendIncrement(EconomicResource object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicResource (object)
    EconomicResourceExistsState newState = new EconomicResourceExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendDecrement(EconomicResource object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicResource (object)
    EconomicResourceExistsState newState = new EconomicResourceExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrIncrement(EconomicResource object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicResource (object)
    EconomicResourceExistsState newState = new EconomicResourceExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrDecrement(EconomicResource object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicResource (object)
    EconomicResourceExistsState newState = new EconomicResourceExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendDependentView(EconomicResource object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicResource (object)
    EconomicResourceExistsState newState = new EconomicResourceExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrDependentView(EconomicResource object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicResource (object)
    EconomicResourceExistsState newState = new EconomicResourceExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrStockFlow(EconomicResource object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicResource (object)
    EconomicResourceExistsState newState = new EconomicResourceExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendStockFlow(EconomicResource object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicResource (object)
    EconomicResourceExistsState newState = new EconomicResourceExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_endLastDependenctView(EconomicResource object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicResource (object)
    EconomicResourceExistsState newState = new EconomicResourceExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendOwnership(EconomicResource object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicResource (object)
    EconomicResourceExistsState newState = new EconomicResourceExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }

  //--- Ending Events ---
  @Override
  public void handle_EVendEconomicResource(EconomicResource object, Context ctx) throws FailedEventHandlingException {

    //Check if there are living  dependents before ending the object
    if(StubHelper.hasLivingDependents(ctx, object))
      throw new FailedEventHandlingException("EconomicResource (" + object.getId() + ") has living dependents and therefore cannot be ended");

    //Changes the state of the current EconomicResource (object)
    EconomicResourceEndedState newState = new EconomicResourceEndedState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
}
