
package at.poquito.assetmanager.it.server;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import at.poquito.assetmanager.event.AssetRemoved;
import at.poquito.assetmanager.event.AssetRetrieved;
import at.poquito.assetmanager.event.AssetStored;
import at.poquito.assetmanager.event.TaskExecuted;

/**
 * @author mario.rodler@gmx.net
 * 
 */
@ApplicationScoped
public class PrintAuditMessages {

	public void removed(@Observes AssetRemoved event) {
		printAuditEvent(event.toString());
	}

	private void printAuditEvent(String event) {
		System.out.println("AUDIT:" + event);
	}

	public void retrieved(@Observes AssetRetrieved event) {
		printAuditEvent(event.toString());
	}

	public void stored(@Observes AssetStored event) {
		printAuditEvent(event.toString());
	}

	public void processed(@Observes TaskExecuted event) {
		printAuditEvent(event.toString());
	}

}
