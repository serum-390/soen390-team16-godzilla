package ca.serum390.godzilla.experimental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComponentBoi {

    @Autowired
    private BigBoi myBoi;

    public String lookWhatYouDidToMyBoi() {
        return "Look what you did to my boi! "
               + myBoi.getFirstName()
               + " " + myBoi.getLastName()
               + " " + myBoi.getId();
    }
}
