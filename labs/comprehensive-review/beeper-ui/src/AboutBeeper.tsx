import { useState } from "react";
import {
  AboutModal,
  TextContent,
  Text,
  TextList,
  TextListItem,
} from "@patternfly/react-core";

import "./roll.css";

export function AboutBeeper(props: { onClose?: Function }) {
  const [isRolling, setIsRolling] = useState(false);

  function doBarrelRoll() {
    setIsRolling(true);
    setTimeout(() => setIsRolling(false), 1900);
  }

  return (
    <AboutModal
      isOpen
      onClose={() => props.onClose?.()}
      trademark="© 2022"
      productName="Beeper"
      brandImageSrc={""}
      brandImageAlt={""}
      className={isRolling ? "rollme" : ""}
    >
      <TextContent>
        <Text>
          Beeper is a simple messaging application. Create and rate Beeps.
        </Text>
        <br />
        <br />
        <br />
        <br />
        <TextList component="dl">
          <TextListItem component="dt">Browser</TextListItem>
          <TextListItem component="dd">Exists</TextListItem>
          <TextListItem component="dt">User Name</TextListItem>
          <TextListItem component="dd">???</TextListItem>
          <TextListItem component="dt">User Role</TextListItem>
          <TextListItem component="dd">???</TextListItem>
          <TextListItem component="dt">Barrel Roll</TextListItem>
          <TextListItem component="dd">
            <a onClick={doBarrelRoll}>Do it</a>
          </TextListItem>
        </TextList>
      </TextContent>
    </AboutModal>
  );
}
