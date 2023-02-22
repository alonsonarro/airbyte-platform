# coding: utf-8

from __future__ import annotations
from datetime import date, datetime  # noqa: F401

import re  # noqa: F401
from typing import Any, Dict, List, Optional  # noqa: F401

from pydantic import AnyUrl, BaseModel, EmailStr, Field, validator  # noqa: F401
from connector_builder.generated.models.http_request import HttpRequest
from connector_builder.generated.models.http_response import HttpResponse


class StreamReadSlicesInnerPagesInner(BaseModel):
    """NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).

    Do not edit the class manually.

    StreamReadSlicesInnerPagesInner - a model defined in OpenAPI

        records: The records of this StreamReadSlicesInnerPagesInner.
        request: The request of this StreamReadSlicesInnerPagesInner [Optional].
        response: The response of this StreamReadSlicesInnerPagesInner [Optional].
    """

    records: List[object] = Field(alias="records")
    request: Optional[HttpRequest] = Field(alias="request", default=None)
    response: Optional[HttpResponse] = Field(alias="response", default=None)

StreamReadSlicesInnerPagesInner.update_forward_refs()
