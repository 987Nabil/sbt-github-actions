/*
 * Copyright 2020 Daniel Spiewak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sbtghactions

import sbtghactions.RenderFunctions.{indentOnce, wrap}

final case class Workflow(
    name: String,
    ons: Seq[TriggerEvent],
    jobs: Seq[WorkflowJob],
    env: Map[String, String]) {

  def render: String =
    s"""|name: ${wrap(name)}
        |
        |on:\n${ons.map(_.render).map(indentOnce).mkString("\n")}""".stripMargin
}