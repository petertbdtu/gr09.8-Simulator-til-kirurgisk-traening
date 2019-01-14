package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;

public interface IRecycleViewAdapterListener {
    public void VaelgBrugsscenarie(String Id);
    public void SeLog(String Id);
    public void SletTablet(String Id);

    public void sendBrugsscenarie(Scenario brugsscencarie);
}



