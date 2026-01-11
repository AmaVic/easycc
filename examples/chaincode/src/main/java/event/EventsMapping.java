package event;

import entity.EconomicResource;
import entity.EconomicEvent;
import entity.EconomicAgent;
import entity.StockFlow;
import entity.Duality;
import entity.Participation;
import entity.Increment;
import entity.Ownership;
import entity.Decrement;
import entity.DependentView;

import runtime.BusinessEvent;
import runtime.exception.BusinessEventNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class EventsMapping {
  private static final EventsMapping instance = new EventsMapping();
  private Map<String, BusinessEvent> events;

  private EventsMapping() {
     this.events = new HashMap<String, BusinessEvent>();
     this.loadEvents();
  }

  public boolean eventExists(String eventName) {
return this.events.containsKey(eventName);
}

  public BusinessEvent getBusinessEvent(String name) throws BusinessEventNotFoundException {
    if(!this.eventExists(name))
      throw new BusinessEventNotFoundException("EventsMapping.getBusinessEvent(String): event with name" + name + " not found");

    return this.events.get(name);
  }

  private void loadEvents() {
    BusinessEvent EVcrEconomicResource = new BusinessEvent("EVcrEconomicResource", BusinessEvent.BusinessEventType.CREATE, EconomicResource.class);
    this.events.put(EVcrEconomicResource.getName(), EVcrEconomicResource);

    BusinessEvent EVendEconomicResource = new BusinessEvent("EVendEconomicResource", BusinessEvent.BusinessEventType.END, EconomicResource.class);
    this.events.put(EVendEconomicResource.getName(), EVendEconomicResource);

    BusinessEvent EVcrEconomicEvent = new BusinessEvent("EVcrEconomicEvent", BusinessEvent.BusinessEventType.CREATE, EconomicEvent.class);
    this.events.put(EVcrEconomicEvent.getName(), EVcrEconomicEvent);

    BusinessEvent EVendEconomicEvent = new BusinessEvent("EVendEconomicEvent", BusinessEvent.BusinessEventType.END, EconomicEvent.class);
    this.events.put(EVendEconomicEvent.getName(), EVendEconomicEvent);

    BusinessEvent EVcrEconomicAgent = new BusinessEvent("EVcrEconomicAgent", BusinessEvent.BusinessEventType.CREATE, EconomicAgent.class);
    this.events.put(EVcrEconomicAgent.getName(), EVcrEconomicAgent);

    BusinessEvent EVendEconomicAgent = new BusinessEvent("EVendEconomicAgent", BusinessEvent.BusinessEventType.END, EconomicAgent.class);
    this.events.put(EVendEconomicAgent.getName(), EVendEconomicAgent);

    BusinessEvent EVcrStockFlow = new BusinessEvent("EVcrStockFlow", BusinessEvent.BusinessEventType.CREATE, StockFlow.class);
    this.events.put(EVcrStockFlow.getName(), EVcrStockFlow);

    BusinessEvent EVendStockFlow = new BusinessEvent("EVendStockFlow", BusinessEvent.BusinessEventType.END, StockFlow.class);
    this.events.put(EVendStockFlow.getName(), EVendStockFlow);

    BusinessEvent EVcrDuality = new BusinessEvent("EVcrDuality", BusinessEvent.BusinessEventType.CREATE, Duality.class);
    this.events.put(EVcrDuality.getName(), EVcrDuality);

    BusinessEvent EVendDuality = new BusinessEvent("EVendDuality", BusinessEvent.BusinessEventType.END, Duality.class);
    this.events.put(EVendDuality.getName(), EVendDuality);

    BusinessEvent EVcrParticipation = new BusinessEvent("EVcrParticipation", BusinessEvent.BusinessEventType.CREATE, Participation.class);
    this.events.put(EVcrParticipation.getName(), EVcrParticipation);

    BusinessEvent EVendParticipation = new BusinessEvent("EVendParticipation", BusinessEvent.BusinessEventType.END, Participation.class);
    this.events.put(EVendParticipation.getName(), EVendParticipation);

    BusinessEvent EVcrIncrement = new BusinessEvent("EVcrIncrement", BusinessEvent.BusinessEventType.CREATE, Increment.class);
    this.events.put(EVcrIncrement.getName(), EVcrIncrement);

    BusinessEvent EVendIncrement = new BusinessEvent("EVendIncrement", BusinessEvent.BusinessEventType.END, Increment.class);
    this.events.put(EVendIncrement.getName(), EVendIncrement);

    BusinessEvent EVcrOwnership = new BusinessEvent("EVcrOwnership", BusinessEvent.BusinessEventType.CREATE, Ownership.class);
    this.events.put(EVcrOwnership.getName(), EVcrOwnership);

    BusinessEvent EVendOwnership = new BusinessEvent("EVendOwnership", BusinessEvent.BusinessEventType.END, Ownership.class);
    this.events.put(EVendOwnership.getName(), EVendOwnership);

    BusinessEvent EVcrDecrement = new BusinessEvent("EVcrDecrement", BusinessEvent.BusinessEventType.CREATE, Decrement.class);
    this.events.put(EVcrDecrement.getName(), EVcrDecrement);

    BusinessEvent EVendDecrement = new BusinessEvent("EVendDecrement", BusinessEvent.BusinessEventType.END, Decrement.class);
    this.events.put(EVendDecrement.getName(), EVendDecrement);

    BusinessEvent EVcrDependentView = new BusinessEvent("EVcrDependentView", BusinessEvent.BusinessEventType.CREATE, DependentView.class);
    this.events.put(EVcrDependentView.getName(), EVcrDependentView);

    BusinessEvent EVendDependentView = new BusinessEvent("EVendDependentView", BusinessEvent.BusinessEventType.END, DependentView.class);
    this.events.put(EVendDependentView.getName(), EVendDependentView);

    BusinessEvent endLastDependenctView = new BusinessEvent("endLastDependenctView", BusinessEvent.BusinessEventType.END, DependentView.class);
    this.events.put(endLastDependenctView.getName(), endLastDependenctView);

  }

public static EventsMapping instance() { return instance; }

}