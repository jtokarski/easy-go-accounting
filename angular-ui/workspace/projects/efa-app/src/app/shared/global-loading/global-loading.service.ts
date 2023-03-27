import { Injectable } from '@angular/core';
import { AsyncTasksLocalTracker, AsyncTasksView } from "@defendev/common-angular";
import { AsyncTasksTreeNode, AsyncTasksTreeAggregateNode, buildRecursiveRootView }
  from "@defendev/common-angular";


type EfaAsyncTasksTracker = AsyncTasksTreeAggregateNode & {
  sourceDocumentMain: AsyncTasksTreeNode & {
    sourceDocumentBrowse: AsyncTasksTreeNode & { }
  }
}

@Injectable({
  providedIn: 'root'
})
export class GlobalLoadingService {

  public readonly tracker: EfaAsyncTasksTracker;

  constructor() {
    this.tracker = {
      _: new AsyncTasksLocalTracker(),
      sourceDocumentMain: {
        _: new AsyncTasksLocalTracker(),
        sourceDocumentBrowse: {
          _: new AsyncTasksLocalTracker(),
        }
      },
      _all: { } as AsyncTasksView,
    };
    this.tracker._all = buildRecursiveRootView(this.tracker);
  }

}
