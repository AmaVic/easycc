package state.dependentView;

import entity.DependentView;
import entity.Participation;
import entity.Decrement;
import entity.StockFlow;
import entity.Increment;
import entity.EconomicAgent;
import entity.Duality;
import entity.EconomicEvent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class DependentViewExistsState extends DependentViewState {
  //========== Constructor ===========
  public DependentViewExistsState() {
    super("exists", StateType.ONGOING);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---

  //--- Ending Events ---
  @Override
  public void handle_endLastDependenctView(DependentView object, Context ctx) throws FailedEventHandlingException {

    //Check if there are living  dependents before ending the object
    if(StubHelper.hasLivingDependents(ctx, object))
      throw new FailedEventHandlingException("DependentView (" + object.getId() + ") has living dependents and therefore cannot be ended");

    //Changes the state of the current DependentView (object)
    DependentViewEndedState newState = new DependentViewEndedState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //Increment
    Increment increment_Increment_DependentView = (Increment)StubHelper.findBusinessObject(ctx, object.getIncrementId_Increment_DependentView());
    increment_Increment_DependentView.getCurrentState().handle_endLastDependenctView(increment_Increment_DependentView, ctx);
    //Decrement
    Decrement decrement_Decrement_DependentView = (Decrement)StubHelper.findBusinessObject(ctx, object.getDecrementId_Decrement_DependentView());
    decrement_Decrement_DependentView.getCurrentState().handle_endLastDependenctView(decrement_Decrement_DependentView, ctx);
  }
  @Override
  public void handle_EVendDependentView(DependentView object, Context ctx) throws FailedEventHandlingException {

    //Check if there are living  dependents before ending the object
    if(StubHelper.hasLivingDependents(ctx, object))
      throw new FailedEventHandlingException("DependentView (" + object.getId() + ") has living dependents and therefore cannot be ended");

    //Changes the state of the current DependentView (object)
    DependentViewEndedState newState = new DependentViewEndedState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //Increment
    Increment increment_Increment_DependentView = (Increment)StubHelper.findBusinessObject(ctx, object.getIncrementId_Increment_DependentView());
    increment_Increment_DependentView.getCurrentState().handle_EVendDependentView(increment_Increment_DependentView, ctx);
    //Decrement
    Decrement decrement_Decrement_DependentView = (Decrement)StubHelper.findBusinessObject(ctx, object.getDecrementId_Decrement_DependentView());
    decrement_Decrement_DependentView.getCurrentState().handle_EVendDependentView(decrement_Decrement_DependentView, ctx);
  }
}
