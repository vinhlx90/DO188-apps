import { useState } from "react";
import {
  Button,
  Form,
  FormGroup,
  HelperText,
  HelperTextItem,
  Modal,
  ModalVariant,
  TextArea,
  TextInput,
} from "@patternfly/react-core";
import { NewBeep } from "@redhattraining/beeper-ui-client";

import { BeepApi } from "./BeepApi";

export default function BeepForm(props: { onSubmit: Function }) {
  const defaultBeep: NewBeep = {
    content: "",
    username: "bob",
  };

  const [newBeep, setNewBeep] = useState<NewBeep>(defaultBeep);
  const [isModalOpen, setIsModalOpen] = useState(false);

  async function createBeep() {
    await BeepApi.create({ newBeep });
    clearForm();
    props.onSubmit();
    setIsModalOpen(false);
  }

  function formCancel() {
    clearForm();
    setIsModalOpen(false);
  }

  function clearForm() {
    setNewBeep(defaultBeep);
  }

  return (
    <>
      <Button onClick={() => setIsModalOpen(true)}>New Beep</Button>
      <Modal
        variant={ModalVariant.small}
        title="Create Beep"
        isOpen={isModalOpen}
        onClose={formCancel}
        actions={[
          <Button
            key="create"
            variant="primary"
            form="modal-with-form-form"
            onClick={() => createBeep()}
          >
            Submit
          </Button>,
          <Button key="cancel" variant="link" onClick={formCancel}>
            Cancel
          </Button>,
        ]}
      >
        <Form onSubmit={(e) => e.preventDefault()}>
          <FormGroup fieldId="username" label="Username" isRequired>
            <TextInput
              isRequired
              id="username"
              name="username"
              value={newBeep.username}
              onChange={(username) => setNewBeep({ ...newBeep, username })}
            />
          </FormGroup>
          <FormGroup fieldId="content" label="Comment" isRequired>
            <TextArea
              isRequired
              cols={30}
              rows={3}
              maxLength={255}
              id="content"
              name="content"
              value={newBeep.content}
              onChange={(content) => setNewBeep({ ...newBeep, content })}
            />
            <HelperText>
              <HelperTextItem
                variant={newBeep.content.length < 255 ? "default" : "error"}
              >
                {255 - newBeep.content.length} characters left
              </HelperTextItem>
            </HelperText>
          </FormGroup>
        </Form>
      </Modal>
    </>
  );
}
