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

public class DependentViewAllocatedState extends DependentViewState {
  //========== Constructor ===========
  public DependentViewAllocatedState() {
    super("allocated", StateType.INITIAL);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  @Override
  public void handle_EVcrDependentView(DependentView object, Context ctx) throws FailedEventHandlingException {
    //Check if the referenced masters exist
    //Increment
    if(!StubHelper.exists(ctx, object.getIncrementId_Increment_DependentView()))
      throw new FailedEventHandlingException("Master with id " + object.getIncrementId_Increment_DependentView() + " does not exist");

    //Retrieve Master (increment_Increment_DependentView)
    Increment increment_Increment_DependentView = (Increment)StubHelper.findBusinessObject(ctx, object.getIncrementId_Increment_DependentView());

    //Master increment can only have one dependent of type DependentView. Checking if one already exists
    if(StubHelper.hasLivingDependentsOfType(ctx, increment_Increment_DependentView, object))
      throw new FailedEventHandlingException("Master with id " + object.getIncrementId_Increment_DependentView() + " already has a living dependent of type DependentView (limited to one)");
    //Decrement
    if(!StubHelper.exists(ctx, object.getDecrementId_Decrement_DependentView()))
      throw new FailedEventHandlingException("Master with id " + object.getDecrementId_Decrement_DependentView() + " does not exist");

    //Retrieve Master (decrement_Decrement_DependentView)
    Decrement decrement_Decrement_DependentView = (Decrement)StubHelper.findBusinessObject(ctx, object.getDecrementId_Decrement_DependentView());

    //Master decrement can only have one dependent of type DependentView. Checking if one already exists
    if(StubHelper.hasLivingDependentsOfType(ctx, decrement_Decrement_DependentView, object))
      throw new FailedEventHandlingException("Master with id " + object.getDecrementId_Decrement_DependentView() + " already has a living dependent of type DependentView (limited to one)");

    //Checking Multiple Propagation Constraints
    //Checking Multiple Propagation Constraint 714
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath714Supplier = () -> {
      //Fetching Increment
      Increment mpc_increment_Increment_DependentView = null;
      try {
        mpc_increment_Increment_DependentView = (Increment) StubHelper.findBusinessObject(ctx, object.getIncrementId_Increment_DependentView());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching Participation
      Participation mpc_participation_Participation_Increment = null;
      try {
      mpc_participation_Participation_Increment = (Participation) StubHelper.findBusinessObject(ctx, mpc_increment_Increment_DependentView.getParticipationId_Participation_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_EconomicEvent_Participation = null;
      try {
      mpc_economicEvent_EconomicEvent_Participation = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_participation_Participation_Increment.getEconomicEventId_EconomicEvent_Participation());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_EconomicEvent_Participation;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath714Supplier = () -> {
      //Fetching Decrement
      Decrement mpc_decrement_Decrement_DependentView = null;
      try {
        mpc_decrement_Decrement_DependentView = (Decrement) StubHelper.findBusinessObject(ctx, object.getDecrementId_Decrement_DependentView());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching Participation
      Participation mpc_participation_Participation_Decrement = null;
      try {
      mpc_participation_Participation_Decrement = (Participation) StubHelper.findBusinessObject(ctx, mpc_decrement_Decrement_DependentView.getParticipationId_Participation_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_EconomicEvent_Participation = null;
      try {
      mpc_economicEvent_EconomicEvent_Participation = (EconomicEvent) StubHelper.findBusinessObject(ctx, mpc_participation_Participation_Decrement.getEconomicEventId_EconomicEvent_Participation());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicEvent_EconomicEvent_Participation;
    };
 
    if(ultimateMPCObjectEnabledPath714Supplier.get().getId().equals(ultimateMPCObjectDisabledPath714Supplier.get().getId()))
      throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    //Checking Multiple Propagation Constraint 719
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath719Supplier = () -> {
      //Fetching Increment
      Increment mpc_increment_Increment_DependentView = null;
      try {
        mpc_increment_Increment_DependentView = (Increment) StubHelper.findBusinessObject(ctx, object.getIncrementId_Increment_DependentView());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching Participation
      Participation mpc_participation_Participation_Increment = null;
      try {
      mpc_participation_Participation_Increment = (Participation) StubHelper.findBusinessObject(ctx, mpc_increment_Increment_DependentView.getParticipationId_Participation_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicAgent
      EconomicAgent mpc_economicAgent_EconomicAgent_Participation = null;
      try {
      mpc_economicAgent_EconomicAgent_Participation = (EconomicAgent) StubHelper.findBusinessObject(ctx, mpc_participation_Participation_Increment.getEconomicAgentId_EconomicAgent_Participation());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicAgent_EconomicAgent_Participation;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath719Supplier = () -> {
      //Fetching Decrement
      Decrement mpc_decrement_Decrement_DependentView = null;
      try {
        mpc_decrement_Decrement_DependentView = (Decrement) StubHelper.findBusinessObject(ctx, object.getDecrementId_Decrement_DependentView());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching Participation
      Participation mpc_participation_Participation_Decrement = null;
      try {
      mpc_participation_Participation_Decrement = (Participation) StubHelper.findBusinessObject(ctx, mpc_decrement_Decrement_DependentView.getParticipationId_Participation_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching EconomicAgent
      EconomicAgent mpc_economicAgent_EconomicAgent_Participation = null;
      try {
      mpc_economicAgent_EconomicAgent_Participation = (EconomicAgent) StubHelper.findBusinessObject(ctx, mpc_participation_Participation_Decrement.getEconomicAgentId_EconomicAgent_Participation());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_economicAgent_EconomicAgent_Participation;
    };
 
    if(!ultimateMPCObjectEnabledPath719Supplier.get().getId().equals(ultimateMPCObjectDisabledPath719Supplier.get().getId()))
      throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    //Checking Multiple Propagation Constraint 722
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath722Supplier = () -> {
      //Fetching Increment
      Increment mpc_increment_Increment_DependentView = null;
      try {
        mpc_increment_Increment_DependentView = (Increment) StubHelper.findBusinessObject(ctx, object.getIncrementId_Increment_DependentView());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching Participation
      Participation mpc_participation_Participation_Increment = null;
      try {
      mpc_participation_Participation_Increment = (Participation) StubHelper.findBusinessObject(ctx, mpc_increment_Increment_DependentView.getParticipationId_Participation_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_participation_Participation_Increment;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath722Supplier = () -> {
      //Fetching Decrement
      Decrement mpc_decrement_Decrement_DependentView = null;
      try {
        mpc_decrement_Decrement_DependentView = (Decrement) StubHelper.findBusinessObject(ctx, object.getDecrementId_Decrement_DependentView());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching Participation
      Participation mpc_participation_Participation_Decrement = null;
      try {
      mpc_participation_Participation_Decrement = (Participation) StubHelper.findBusinessObject(ctx, mpc_decrement_Decrement_DependentView.getParticipationId_Participation_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_participation_Participation_Decrement;
    };
 
    if(ultimateMPCObjectEnabledPath722Supplier.get().getId().equals(ultimateMPCObjectDisabledPath722Supplier.get().getId()))
      throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    //Checking Multiple Propagation Constraint 725
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath725Supplier = () -> {
      //Fetching Increment
      Increment mpc_increment_Increment_DependentView = null;
      try {
        mpc_increment_Increment_DependentView = (Increment) StubHelper.findBusinessObject(ctx, object.getIncrementId_Increment_DependentView());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching StockFlow
      StockFlow mpc_stockFlow_StockFlow_Increment = null;
      try {
      mpc_stockFlow_StockFlow_Increment = (StockFlow) StubHelper.findBusinessObject(ctx, mpc_increment_Increment_DependentView.getStockFlowId_StockFlow_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_stockFlow_StockFlow_Increment;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath725Supplier = () -> {
      //Fetching Decrement
      Decrement mpc_decrement_Decrement_DependentView = null;
      try {
        mpc_decrement_Decrement_DependentView = (Decrement) StubHelper.findBusinessObject(ctx, object.getDecrementId_Decrement_DependentView());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching StockFlow
      StockFlow mpc_stockFlow_StockFlow_Decrement = null;
      try {
      mpc_stockFlow_StockFlow_Decrement = (StockFlow) StubHelper.findBusinessObject(ctx, mpc_decrement_Decrement_DependentView.getStockFlowId_StockFlow_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_stockFlow_StockFlow_Decrement;
    };
 
    if(ultimateMPCObjectEnabledPath725Supplier.get().getId().equals(ultimateMPCObjectDisabledPath725Supplier.get().getId()))
      throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    //Checking Multiple Propagation Constraint 728
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath728Supplier = () -> {
      //Fetching Increment
      Increment mpc_increment_Increment_DependentView = null;
      try {
        mpc_increment_Increment_DependentView = (Increment) StubHelper.findBusinessObject(ctx, object.getIncrementId_Increment_DependentView());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching Duality
      Duality mpc_duality_Duality_Increment = null;
      try {
      mpc_duality_Duality_Increment = (Duality) StubHelper.findBusinessObject(ctx, mpc_increment_Increment_DependentView.getDualityId_Duality_Increment());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_duality_Duality_Increment;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath728Supplier = () -> {
      //Fetching Decrement
      Decrement mpc_decrement_Decrement_DependentView = null;
      try {
        mpc_decrement_Decrement_DependentView = (Decrement) StubHelper.findBusinessObject(ctx, object.getDecrementId_Decrement_DependentView());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      //Fetching Duality
      Duality mpc_duality_Duality_Decrement = null;
      try {
      mpc_duality_Duality_Decrement = (Duality) StubHelper.findBusinessObject(ctx, mpc_decrement_Decrement_DependentView.getDualityId_Duality_Decrement());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }

      return mpc_duality_Duality_Decrement;
    };
 
    if(!ultimateMPCObjectEnabledPath728Supplier.get().getId().equals(ultimateMPCObjectDisabledPath728Supplier.get().getId()))
      throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    //Changes the state of the current DependentView (object)
    DependentViewExistsState newState = new DependentViewExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //Increment
    increment_Increment_DependentView.getCurrentState().handle_EVcrDependentView(increment_Increment_DependentView, ctx);

    //Decrement
    decrement_Decrement_DependentView.getCurrentState().handle_EVcrDependentView(decrement_Decrement_DependentView, ctx);

  }
  //--- Modifying Events ---

  //--- Ending Events ---
}
