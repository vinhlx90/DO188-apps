import { Configuration, DefaultApi } from "@redhattraining/beeper-ui-client";

export const BeepApi = new DefaultApi(
  new Configuration({
    // send API requests to origin in all cases to let nginx handle proxying to API
    basePath: "/api",
  })
);
