import { useState } from "react";
import { Beep } from "@redhattraining/beeper-ui-client";
import {
  Card,
  CardTitle,
  CardBody,
  ActionList,
  ActionListGroup,
  ActionListItem,
  CardHeader,
  CardActions,
} from "@patternfly/react-core";
import { Button, Text } from "@patternfly/react-core";

import { BeepApi } from "./BeepApi";
import {
  ArrowDownIcon,
  ArrowUpIcon,
  PencilAltIcon,
  TrashIcon,
} from "@patternfly/react-icons";
import { ConfirmationPrompt } from "./ConfirmationPrompt";

export default function BeepCard(props: { beep: Beep; onDelete: Function }) {
  const { id } = props.beep;

  const [votes, setVotes] = useState<number>(props.beep.votes ?? 0);
  const [isConfirmationOpen, setIsConfirmationOpen] = useState(false);
  const [isHoverEdit, setIsHoverEdit] = useState(false);

  async function upvote() {
    await BeepApi.beepUpvote({ id });
    setVotes(votes + 1);
  }

  async function downvote() {
    await BeepApi.beepDownvote({ id });
    setVotes(votes - 1);
  }

  async function deleteBeep() {
    await BeepApi.deleteBeep({ id });
    props.onDelete();
  }

  return (
    <Card isCompact>
      <CardHeader>
        <CardTitle>{props.beep.username}</CardTitle>
        <CardActions>
          <Button
            variant="primary"
            isSmall
            onMouseOver={() => setIsHoverEdit(true)}
            isDisabled={isHoverEdit}
          >
            {isHoverEdit ? <Text>Coming soon</Text> : <PencilAltIcon />}
          </Button>
          <Button
            variant="danger"
            isSmall
            onClick={() => setIsConfirmationOpen(true)}
          >
            <TrashIcon />
          </Button>
        </CardActions>
      </CardHeader>
      <CardBody>
        <ConfirmationPrompt
          message="Are you sure you want to delete this beep?"
          isOpen={isConfirmationOpen}
          submitType="danger"
          onSubmit={(confirmed) => {
            if (confirmed) {
              deleteBeep();
            }
            setIsConfirmationOpen(false);
          }}
        />
        {props.beep.content}
        <ActionList>
          <ActionListGroup>
            <ActionListItem>
              <Button onClick={upvote} variant="control" isSmall>
                <ArrowUpIcon />
              </Button>
              <Button variant="control" isSmall isDisabled>
                {votes}
              </Button>
              <Button onClick={downvote} variant="control" isSmall>
                <ArrowDownIcon />
              </Button>
            </ActionListItem>
          </ActionListGroup>
        </ActionList>
      </CardBody>
    </Card>
  );
}
