import { Route } from '@angular/router';

import { JhiManagementApiComponent } from './management-api.component';

export const managementApiRoute: Route = {
    path: 'management-api',
    component: JhiManagementApiComponent,
    data: {
        pageTitle: 'global.menu.admin.apidocs'
    }
};
