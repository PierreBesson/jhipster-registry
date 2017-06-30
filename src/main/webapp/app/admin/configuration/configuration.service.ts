import { Injectable } from '@angular/core';
import { Http, Response, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Route } from '../../shared';

@Injectable()
export class JhiConfigurationService {

    constructor(private http: Http) {
    }

    getConfigs(prefix: String = ''): Observable<any> {
        return this.http.get(prefix + 'management/configprops').map((res: Response) => {
            const properties: any[] = [];
            const propertiesObject = res.json();

            for (const key in propertiesObject) {
                if (propertiesObject.hasOwnProperty(key)) {
                    properties.push(propertiesObject[key]);
                }
            }

            return properties.sort((propertyA, propertyB) => {
                return (propertyA.prefix === propertyB.prefix) ? 0 :
                    (propertyA.prefix < propertyB.prefix) ? -1 : 1;
            });
        });
    }

    getInstanceConfigs(instance: Route): Observable<any> {
        if (instance && instance.prefix && instance.prefix.length > 0) {
            return this.getConfigs(instance.prefix + '/');
        }
        return this.getConfigs();
    }

    getEnv(prefix: String = ''): Observable<any> {
        return this.http.get(prefix + 'management/env').map((res: Response) => {
            const properties: any = {};
            const propertiesObject = res.json();

            for (const key in propertiesObject) {
                if (propertiesObject.hasOwnProperty(key)) {
                    const valsObject = propertiesObject[key];
                    const vals: any[] = [];

                    for (const valKey in valsObject) {
                        if (valsObject.hasOwnProperty(valKey)) {
                            vals.push({key: valKey, val: valsObject[valKey]});
                        }
                    }
                    properties[key] = vals;
                }
            }

            return properties;
        });
    }

    getInstanceEnv(instance: Route): Observable<any> {
        console.log(instance);
        if (instance && instance.prefix && instance.prefix.length > 0) {
            return this.getEnv(instance.prefix + '/');
        }
        return this.getEnv();
    }

    refreshInstance(prefix: String = '/'): Observable<any> {
        console.log(prefix + 'management/refresh');
        return this.http.post(prefix + 'management/refresh', {});
    }

    refreshInstanceEnv(instance: Route): Observable<any> {
        if (instance && instance.prefix && instance.prefix.length > 0) {
            return this.refreshInstance(instance.prefix + '/');
        } else {
            this.refreshInstance();
        }
    }
}
