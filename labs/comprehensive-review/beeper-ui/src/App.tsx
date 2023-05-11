import "@patternfly/react-core/dist/styles/base.css";
import { Masthead, MastheadMain } from "@patternfly/react-core";

import { BeepHome } from "./BeepHome";
import { useState } from "react";
import { OutlinedQuestionCircleIcon } from "@patternfly/react-icons";
import { AboutBeeper } from "./AboutBeeper";

export default function App() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <>
      <Masthead id="basic">
        <MastheadMain>Beeper</MastheadMain>
        <OutlinedQuestionCircleIcon onClick={() => setIsModalOpen(true)} />
      </Masthead>
      {isModalOpen && <AboutBeeper onClose={() => setIsModalOpen(false)} />}
      <BeepHome />
    </>
  );
}
