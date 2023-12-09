import { Injectable } from '@angular/core';
import { AsyncTasksLocalTracker, AsyncTasksView } from "@defendev/common-angular";
import { AsyncTasksTreeNode, AsyncTasksTreeAggregateNode, buildRecursiveRootView }
  from "@defendev/common-angular";


type EfaAsyncTasksTracker = AsyncTasksTreeAggregateNode & {
  documentMain: AsyncTasksTreeNode & {
    documentBrowse: AsyncTasksTreeNode & { }
  }
}

@Injectable({
  providedIn: 'root'
})
export class GlobalLoadingService {

  public readonly tracker: EfaAsyncTasksTracker;

  constructor() {
    this.tracker = {
      _own: new AsyncTasksLocalTracker(),
      documentMain: {
        _own: new AsyncTasksLocalTracker(),
        documentBrowse: {
          _own: new AsyncTasksLocalTracker(),
        }
      },
      _all: { } as AsyncTasksView,
    };
    this.tracker._all = buildRecursiveRootView(this.tracker);
  }

}
