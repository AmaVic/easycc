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

public class OwnershipAllocatedState extends OwnershipState {
  //========== Constructor ===========
  public OwnershipAllocatedState() {
    super("allocated", StateType.INITIAL);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  @Override
  public void handle_EVcrOwnership(Ownership object, Context ctx) throws FailedEventHandlingException {
    //Check if the referenced masters exist
    //EconomicResource
    if(!StubHelper.exists(ctx, object.getEconomicResourceId_EconomicResource_Ownership()))
      throw new FailedEventHandlingException("Master with id " + object.getEconomicResourceId_EconomicResource_Ownership() + " does not exist");

    //Retrieve Master (economicResource_EconomicResource_Ownership)
    EconomicResource economicResource_EconomicResource_Ownership = (EconomicResource)StubHelper.findBusinessObject(ctx, object.getEconomicResourceId_EconomicResource_Ownership());

    //EconomicAgent
    if(!StubHelper.exists(ctx, object.getEconomicAgentId_EconomicAgent_Ownership()))
      throw new FailedEventHandlingException("Master with id " + object.getEconomicAgentId_EconomicAgent_Ownership() + " does not exist");

    //Retrieve Master (economicAgent_EconomicAgent_Ownership)
    EconomicAgent economicAgent_EconomicAgent_Ownership = (EconomicAgent)StubHelper.findBusinessObject(ctx, object.getEconomicAgentId_EconomicAgent_Ownership());


    //Changes the state of the current Ownership (object)
    OwnershipImageState newState = new OwnershipImageState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicResource
    economicResource_EconomicResource_Ownership.getCurrentState().handle_EVcrOwnership(economicResource_EconomicResource_Ownership, ctx);

    //EconomicAgent
    economicAgent_EconomicAgent_Ownership.getCurrentState().handle_EVcrOwnership(economicAgent_EconomicAgent_Ownership, ctx);

  }
  //--- Modifying Events ---

  //--- Ending Events ---
}
