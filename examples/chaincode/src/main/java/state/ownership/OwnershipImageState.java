package state.ownership;

import entity.Ownership;
import entity.EconomicResource;
import entity.EconomicAgent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class OwnershipImageState extends OwnershipState {
  //========== Constructor ===========
  public OwnershipImageState() {
    super("image", StateType.ONGOING);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---
  @Override
  public void handle_EVendDependentView(Ownership object, Context ctx) throws FailedEventHandlingException {

    //The Ownership object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Ownership currentLedgerObject = (Ownership)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getEconomicResourceId_EconomicResource_Ownership().equals(currentLedgerObject.getEconomicResourceId_EconomicResource_Ownership()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getEconomicAgentId_EconomicAgent_Ownership().equals(currentLedgerObject.getEconomicAgentId_EconomicAgent_Ownership()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Ownership (object)
    OwnershipImageState newState = new OwnershipImageState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicResource
    EconomicResource economicResource_EconomicResource_Ownership = (EconomicResource)StubHelper.findBusinessObject(ctx, object.getEconomicResourceId_EconomicResource_Ownership());
    economicResource_EconomicResource_Ownership.getCurrentState().handle_EVendDependentView(economicResource_EconomicResource_Ownership, ctx);
    //EconomicAgent
    EconomicAgent economicAgent_EconomicAgent_Ownership = (EconomicAgent)StubHelper.findBusinessObject(ctx, object.getEconomicAgentId_EconomicAgent_Ownership());
    economicAgent_EconomicAgent_Ownership.getCurrentState().handle_EVendDependentView(economicAgent_EconomicAgent_Ownership, ctx);
  }
  @Override
  public void handle_EVcrIncrement(Ownership object, Context ctx) throws FailedEventHandlingException {

    //The Ownership object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Ownership currentLedgerObject = (Ownership)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getEconomicResourceId_EconomicResource_Ownership().equals(currentLedgerObject.getEconomicResourceId_EconomicResource_Ownership()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getEconomicAgentId_EconomicAgent_Ownership().equals(currentLedgerObject.getEconomicAgentId_EconomicAgent_Ownership()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Ownership (object)
    OwnershipInOwnershipState newState = new OwnershipInOwnershipState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicResource
    EconomicResource economicResource_EconomicResource_Ownership = (EconomicResource)StubHelper.findBusinessObject(ctx, object.getEconomicResourceId_EconomicResource_Ownership());
    economicResource_EconomicResource_Ownership.getCurrentState().handle_EVcrIncrement(economicResource_EconomicResource_Ownership, ctx);
    //EconomicAgent
    EconomicAgent economicAgent_EconomicAgent_Ownership = (EconomicAgent)StubHelper.findBusinessObject(ctx, object.getEconomicAgentId_EconomicAgent_Ownership());
    economicAgent_EconomicAgent_Ownership.getCurrentState().handle_EVcrIncrement(economicAgent_EconomicAgent_Ownership, ctx);
  }
  @Override
  public void handle_EVendDecrement(Ownership object, Context ctx) throws FailedEventHandlingException {

    //The Ownership object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Ownership currentLedgerObject = (Ownership)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getEconomicResourceId_EconomicResource_Ownership().equals(currentLedgerObject.getEconomicResourceId_EconomicResource_Ownership()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getEconomicAgentId_EconomicAgent_Ownership().equals(currentLedgerObject.getEconomicAgentId_EconomicAgent_Ownership()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Ownership (object)
    OwnershipImageState newState = new OwnershipImageState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicResource
    EconomicResource economicResource_EconomicResource_Ownership = (EconomicResource)StubHelper.findBusinessObject(ctx, object.getEconomicResourceId_EconomicResource_Ownership());
    economicResource_EconomicResource_Ownership.getCurrentState().handle_EVendDecrement(economicResource_EconomicResource_Ownership, ctx);
    //EconomicAgent
    EconomicAgent economicAgent_EconomicAgent_Ownership = (EconomicAgent)StubHelper.findBusinessObject(ctx, object.getEconomicAgentId_EconomicAgent_Ownership());
    economicAgent_EconomicAgent_Ownership.getCurrentState().handle_EVendDecrement(economicAgent_EconomicAgent_Ownership, ctx);
  }
  @Override
  public void handle_EVendIncrement(Ownership object, Context ctx) throws FailedEventHandlingException {

    //The Ownership object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Ownership currentLedgerObject = (Ownership)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getEconomicResourceId_EconomicResource_Ownership().equals(currentLedgerObject.getEconomicResourceId_EconomicResource_Ownership()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getEconomicAgentId_EconomicAgent_Ownership().equals(currentLedgerObject.getEconomicAgentId_EconomicAgent_Ownership()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Ownership (object)
    OwnershipImageState newState = new OwnershipImageState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicResource
    EconomicResource economicResource_EconomicResource_Ownership = (EconomicResource)StubHelper.findBusinessObject(ctx, object.getEconomicResourceId_EconomicResource_Ownership());
    economicResource_EconomicResource_Ownership.getCurrentState().handle_EVendIncrement(economicResource_EconomicResource_Ownership, ctx);
    //EconomicAgent
    EconomicAgent economicAgent_EconomicAgent_Ownership = (EconomicAgent)StubHelper.findBusinessObject(ctx, object.getEconomicAgentId_EconomicAgent_Ownership());
    economicAgent_EconomicAgent_Ownership.getCurrentState().handle_EVendIncrement(economicAgent_EconomicAgent_Ownership, ctx);
  }
  @Override
  public void handle_endLastDependenctView(Ownership object, Context ctx) throws FailedEventHandlingException {

    //The Ownership object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Ownership currentLedgerObject = (Ownership)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getEconomicResourceId_EconomicResource_Ownership().equals(currentLedgerObject.getEconomicResourceId_EconomicResource_Ownership()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getEconomicAgentId_EconomicAgent_Ownership().equals(currentLedgerObject.getEconomicAgentId_EconomicAgent_Ownership()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Ownership (object)
    OwnershipImageState newState = new OwnershipImageState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicResource
    EconomicResource economicResource_EconomicResource_Ownership = (EconomicResource)StubHelper.findBusinessObject(ctx, object.getEconomicResourceId_EconomicResource_Ownership());
    economicResource_EconomicResource_Ownership.getCurrentState().handle_endLastDependenctView(economicResource_EconomicResource_Ownership, ctx);
    //EconomicAgent
    EconomicAgent economicAgent_EconomicAgent_Ownership = (EconomicAgent)StubHelper.findBusinessObject(ctx, object.getEconomicAgentId_EconomicAgent_Ownership());
    economicAgent_EconomicAgent_Ownership.getCurrentState().handle_endLastDependenctView(economicAgent_EconomicAgent_Ownership, ctx);
  }

  //--- Ending Events ---
  @Override
  public void handle_EVendOwnership(Ownership object, Context ctx) throws FailedEventHandlingException {

    //Check if there are living  dependents before ending the object
    if(StubHelper.hasLivingDependents(ctx, object))
      throw new FailedEventHandlingException("Ownership (" + object.getId() + ") has living dependents and therefore cannot be ended");

    //Changes the state of the current Ownership (object)
    OwnershipArchivedState newState = new OwnershipArchivedState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicResource
    EconomicResource economicResource_EconomicResource_Ownership = (EconomicResource)StubHelper.findBusinessObject(ctx, object.getEconomicResourceId_EconomicResource_Ownership());
    economicResource_EconomicResource_Ownership.getCurrentState().handle_EVendOwnership(economicResource_EconomicResource_Ownership, ctx);
    //EconomicAgent
    EconomicAgent economicAgent_EconomicAgent_Ownership = (EconomicAgent)StubHelper.findBusinessObject(ctx, object.getEconomicAgentId_EconomicAgent_Ownership());
    economicAgent_EconomicAgent_Ownership.getCurrentState().handle_EVendOwnership(economicAgent_EconomicAgent_Ownership, ctx);
  }
}
