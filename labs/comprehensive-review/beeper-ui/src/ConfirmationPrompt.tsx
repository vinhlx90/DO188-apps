import {
  Button,
  ButtonProps,
  Modal,
  ModalVariant,
} from "@patternfly/react-core";

export function ConfirmationPrompt(props: {
  message: string;
  isOpen: boolean;
  onSubmit: (confirmed: boolean) => void;
  submitType?: ButtonProps["variant"];
}) {
  return (
    <Modal
      variant={ModalVariant.small}
      isOpen={props.isOpen}
      aria-label="delete confirmation"
      showClose={false}
      onClose={() => props.onSubmit(false)}
      actions={[
        <Button
          key="confirm"
          variant={props.submitType ?? "primary"}
          onClick={() => props.onSubmit(true)}
        >
          Confirm
        </Button>,
        <Button
          key="cancel"
          variant="link"
          onClick={() => props.onSubmit(false)}
        >
          Cancel
        </Button>,
      ]}
    >
      {props.message}
    </Modal>
  );
}
