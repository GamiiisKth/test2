package datagrid;

import org.apache.ignite.Ignition;

/**
 * Created by joshuapro on 08/03/16.
 */
public class StartNode {

    public static void main(String[] args) {
        Ignition.start("test2/config/example-cache.xml");
    }
}
