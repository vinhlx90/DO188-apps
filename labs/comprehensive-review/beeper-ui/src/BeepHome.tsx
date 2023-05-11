import { useEffect, useState } from "react";
import {
  Card,
  CardActions,
  CardBody,
  CardHeader,
  CardTitle,
  List,
  ListItem,
} from "@patternfly/react-core";
import { Beep } from "@redhattraining/beeper-ui-client";

import { BeepApi } from "./BeepApi";
import BeepForm from "./BeepForm";
import BeepCard from "./BeepCard";

export function BeepHome() {
  const [beeps, setBeeps] = useState<Beep[]>([]);

  useEffect(fetchBeeps, []);

  function fetchBeeps() {
    BeepApi.getBeeps().then(setBeeps);
  }

  return (
    <Card isPlain>
      <CardHeader>
        <CardTitle>Beeps</CardTitle>
        <CardActions>
          <BeepForm onSubmit={fetchBeeps} />
        </CardActions>
      </CardHeader>
      <CardBody>
        <List isPlain>
          {beeps.map((beep) => (
            <ListItem key={beep.id}>
              <BeepCard key={beep.id} beep={beep} onDelete={fetchBeeps} />
            </ListItem>
          ))}
        </List>
      </CardBody>
    </Card>
  );
}
