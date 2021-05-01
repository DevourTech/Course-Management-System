package org.cms.client.ui;

import javafx.scene.Node;
import javafx.stage.Stage;

public class UserDialog extends Stage {

	Node sourceNode;

	public Node getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode(Node sourceNode) {
		this.sourceNode = sourceNode;
	}
}
